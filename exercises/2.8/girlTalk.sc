(
// Boot the server
//s.boot;

// Define the path to your audio file
~path = "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/sounds/girlTalkPieces/";

// Replace with your actual file path + chunk_0010.wav";
~numFiles = 16;

~harmonicCenters = [60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86];

// Define a minor scale
~minorScale = [0, 2, 3, 5, 7, 8, 10, 12];

// Load the buffer
~buffers = ~numFiles.collect({ |i|
    var path = ~dir ++ "chunk_00" ++ (i+1).asString.padLeft(2, "0") ++ ".wav";
    Buffer.read(s, path);
});

// Define a simple SynthDef to play the buffer
SynthDef(\player, {
    Out.ar(0, PlayBuf.ar(2, \buf.ir, doneAction: 2))
}).add;

// SynthDef for playing the notes
SynthDef(\notes, {
    |freq = 440, amp = 0.2, gate = 1, pan = 0|
    var sig, env;
    sig = SinOsc.ar(freq);
    env = EnvGen.kr(Env.adsr(0.01, 0.1, 0.5, 0.1), gate, doneAction: 2);
    sig = Pan2.ar(sig * env * amp, pan);
    Out.ar(0, sig);
}).add;

// Function to play the buffer
~play = { | index |
	Synth(\player, [\buf, ~buffers[index]]);
    "Playing buffer".postln;
};

// Function to play a single buffer with notes
~playWithCenterNotes = { |index|
    var buffer = ~buffers[index];
    var harmonicCenter = ~harmonicCenters[index];
    var scale = ~minorScale + harmonicCenter;
    var notes = Array.fill(8, { scale.choose });
    // var noteDur = 0.1;  // Fixed note duration
	var noteDur = (buffer.duration / 8) * 0.9;
	// 90% of buffer duration / 8, leaving 10% for gaps

    if(buffer.notNil, {
        "Playing buffer %: %".format(index, buffer.path).postln;

        // Play the sample
        Synth(\player, [\buf, buffer]);

        // Play the notes
        fork {
            var notePlayer = Synth(\notes, [\amp, 0]);
            notes.do { |note, i|
                notePlayer.set(
                    \freq, note.midicps,
                    \amp, 0.1,
                    \gate, 1,
                    \pan, i.linlin(0, 7, -0.7, 0.7)
                );
                noteDur.wait;
                notePlayer.set(\gate, 0);
                (noteDur * 0.1).wait;  // Small gap between notes
            };
            notePlayer.free;
        };

        buffer.duration.wait;
    }, {
        "Buffer % is nil, skipping.".format(index).warn;
    });
};

// Function to play all buffers in order
~playAll = {
    Routine({
        ~buffers.do { |buf, i|
            ~play.(i);
        };
        "Finished playing all buffers.".postln;
    }).play;
};

// Function to play all buffers in order
~playAllWithCenterNotes = {
    Routine({
        ~buffers.do { |buf, i|
            ~playWithCenterNotes.(i);
        };
        "Finished playing all buffers.".postln;
    }).play;
};
)

)

// To play the buffer, evaluate this line:
~playAllWithCenterNotes.value;

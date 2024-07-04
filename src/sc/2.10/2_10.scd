
(
// Load the audio file into a buffer

~bufferLoaded = {
    "Buffer loaded".postln;
};

b = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/src/sc/2.9/sounds/RickieLeeJones.wav", ~bufferLoaded.value);

// Create a SynthDef for pitch shifting
SynthDef(\pitchShift, {
    arg out = 0, bufnum, rate = 1.0;
    var signal;
    signal = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum) * rate, loop: 0);
    Out.ar(out, signal);
}).add;

// Define the SynthDef outside of the buffer loading process
SynthDef(\adaptivePitchShift, {
    arg out = 0, bufnum;
    var signal, rateEnv;
    
    // Define an envelope for the playback rate
    rateEnv = EnvGen.kr(
        Env.new([2.0, 0.5, 1.0, 0.5], [5, 5, 5], 'linear'), // Adjust times as needed
        doneAction: 2
    );
    
    // Play the buffer with the envelope-controlled rate
    signal = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum) * rateEnv, loop: 0);
    Out.ar(out, signal);
}).add;

// Play the buffer at half speed to pitch shift it
x = Synth(\adaptivePitchShift, [\bufnum, b.bufnum, \rate, 1.4]); // 0.5 for one octave down

)

(
// Load the audio file into a buffer
b = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/src/sc/2.9/sounds/RickieLeeJones.wav");

// Create a SynthDef for pitch shifting
SynthDef(\pitchShift, {
    arg out = 0, bufnum, rate = 1.0;
    var signal;
    signal = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum) * rate, loop: 0);
    Out.ar(out, signal);
}).add;


// Play the buffer at half speed to pitch shift it
x = Synth(\pitchShift, [\bufnum, b.bufnum, \rate, 0.7]); // 0.5 for one octave down

)

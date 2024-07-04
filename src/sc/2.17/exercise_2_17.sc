

(
// SynthDef for basic chorus effect
SynthDef(\basicChorus, {
    arg bufnum=0, out=0, pitchShift=1.02, mix=0.99;
    var original, shifted, output;
    
    // Original playback
    original = PlayBuf.ar(2, bufnum, BufRateScale.kr(bufnum), doneAction: 2);
    
    // Pitch-shifted playback (tape-style)
    shifted = PlayBuf.ar(2, bufnum, BufRateScale.kr(bufnum) * 1.02, doneAction: 2);
    
    // Mix original and shifted signals
    output = (original * (1 - mix)) + (shifted * mix);
    
    Out.ar(out, output);
}).add;

// Function to play our basic chorus effect
~playBasicChorus = { |buffer, pitchShift=1.01, mix=0.5|
    Synth(\basicChorus, [
        \bufnum, buffer,
        \pitchShift, pitchShift,
        \mix, mix
    ]);
};

b = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/src/sc/sounds/Stephen_St_Paul.wav"); // Replace with your audio file path



// Play the effect
~playBasicChorus.(b);

// Print message when finished
(b.duration + 0.1).wait;
"Finished playing".postln;
)




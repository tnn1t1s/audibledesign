(
// SynthDef for dynamic stereo pitch splitting
SynthDef(\dynamicStereoPitchSplit, {
    arg bufnum=0, out=0, splitFreq=1000, maxShift=1.5, splitDuration=5;
    var input, leftChain, rightChain, leftOutput, rightOutput, splitEnv;
    
    input = PlayBuf.ar(2, bufnum, BufRateScale.kr(bufnum), doneAction: 2);
    
    // Create an envelope for the gradual split
    splitEnv = EnvGen.kr(Env([0, 1], [splitDuration], \lin));
    
    // Process left and right channels separately
    leftChain = FFT(LocalBuf(2048), input[0]);
    rightChain = FFT(LocalBuf(2048), input[1]);
    
    // Gradually shift the upper frequencies
    leftChain = PV_BrickWall(leftChain, splitFreq / (SampleRate.ir / 8) * 4 - 1);
    leftChain = PV_BinShift(leftChain, 1 + ((maxShift - 1) * splitEnv));
    
    rightChain = PV_BrickWall(rightChain, splitFreq / (SampleRate.ir / 8) * 4 - 1);
    rightChain = PV_BinShift(rightChain, 1 + ((1/maxShift - 1) * splitEnv));
    
    leftOutput = IFFT(leftChain);
    rightOutput = IFFT(rightChain);
    
    // Crossfade between original and processed sound
    leftOutput = XFade2.ar(input[0], leftOutput, splitEnv * 8 - 1);
    rightOutput = XFade2.ar(input[1], rightOutput, splitEnv * 8 - 1);
    
    Out.ar(out, [leftOutput, rightOutput]);
}).add;

// Function to play our dynamic stereo pitch split effect
~playDynamicStereoPitchSplit = { |buffer, splitFreq=1000, maxShift=1.5, splitDuration=5|
    Synth(\dynamicStereoPitchSplit, [
        \bufnum, buffer,
        \splitFreq, splitFreq,
        \maxShift, maxShift,
        \splitDuration, splitDuration
    ]);
};

b = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/src/sc/sounds/Stephen_St_Paul.wav"); // Replace with your audio file path


// Play the effect
~playDynamicStereoPitchSplit.(b);

)
// Create a buffer with our source sound



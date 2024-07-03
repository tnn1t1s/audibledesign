(
// SynthDef for dual pitch effect
SynthDef(\dualPitch, {
    arg bufnum=0, out=0, pitchShift=1.5, mix=0.5, fftSize=2048;
    var input, original, shifted, chainL, chainR;
    
    input = PlayBuf.ar(2, bufnum, BufRateScale.kr(bufnum), loop: 1);
    original = input;
    
    // Process left and right channels separately for pitch shifting
    chainL = FFT(LocalBuf(fftSize), input[0]);
    chainR = FFT(LocalBuf(fftSize), input[1]);
    
    chainL = PV_BinShift(chainL, pitchShift);
    chainR = PV_BinShift(chainR, pitchShift);
    
    shifted = [IFFT(chainL), IFFT(chainR)];
    
    // Mix original and shifted signals
    Out.ar(out, (original * (1 - mix)) + (shifted * mix));
}).add;

// Create a buffer with our source sound
b = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/src/sc/sounds/Rev909Crowd.mp3");

// Function to play our dual pitch effect
~playDualPitch = { |buffer, pitchShift=1.5, mix=0.5|
    Synth(\dualPitch, [
        \bufnum, buffer,
        \pitchShift, pitchShift,
        \mix, mix,
        \fftSize, 2048
    ]);
};

// Play with dual pitch effect
"Playing with dual pitch effect".postln;
~playDualPitch.(b, 1.5, 0.5);

"Finished".postln;
)

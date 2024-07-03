
// "/Users/dps/Desktop/audible_design/scratch/audibledesign/src/sc/sounds/Rev909Crowd.mp3")

(
// SynthDef for stereo spectral stretching
SynthDef(\spectralStretchStereo, {
    arg bufnum=0, out=0, stretch=1, fftSize=2048;
    var input, chainL, chainR;
    input = PlayBuf.ar(2, bufnum, BufRateScale.kr(bufnum), loop: 1);
    
    // Process left and right channels separately
    chainL = FFT(LocalBuf(fftSize), input[0]);
    chainR = FFT(LocalBuf(fftSize), input[1]);
    
    chainL = PV_BinShift(chainL, stretch);
    chainR = PV_BinShift(chainR, stretch);
    
    Out.ar(out, [IFFT(chainL), IFFT(chainR)]);
}).add;

// Now, let's create a buffer with our source sound
b = Buffer.read(s, "/Users/dp/Desktop/audible_design/scratch/audibledesign/src/sc/sounds/Rev909Crowd.mp3");

// Function to play our stretched sound
~playStretchedBuffer = { |buffer, stretchFactor|
    Synth(\spectralStretchStereo, [
        \bufnum, buffer,
        \stretch, stretchFactor,
        \fftSize, 2048
    ]);
};


~playStretchedBuffer.(b, 0.5);

"Finished".postln;
)



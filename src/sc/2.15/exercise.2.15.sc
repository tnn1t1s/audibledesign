(
// SynthDef for harmoniser approach
SynthDef(\harmoniser, {
    arg bufnum=0, out=0, shift=1.5, mix=0.5;
    var input, shifted, output;
    input = PlayBuf.ar(2, bufnum, BufRateScale.kr(bufnum), loop: 0);
    shifted = PitchShift.ar(input, 0.2, shift);
    output = (input * (1 - mix)) + (shifted * mix);
    Out.ar(out, output);
}).add;

// SynthDef for spectral splitting
SynthDef(\spectralSplit, {
    arg bufnum=0, out=0, splitFreq=1000, upperShift=1.5, mix=0.5, fftSize=2048;
    var input, chain, lower, upper, output;
    input = PlayBuf.ar(2, bufnum, BufRateScale.kr(bufnum), loop: 0);
    chain = FFT(LocalBuf(fftSize), input);
    lower = PV_BrickWall(chain, splitFreq / (SampleRate.ir / 2) * 2 - 1);
    upper = PV_BrickWall(chain, splitFreq / (SampleRate.ir / 2) * 2 - 1, -1);
    upper = PV_BinShift(upper, upperShift);
    output = IFFT(lower) + IFFT(upper);
    output = (input * (1 - mix)) + (output * mix);
    Out.ar(out, output);
}).add;

// Create a buffer with our source sound
b = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/src/sc/sounds/Stephen_St_Paul.wav"); // Replace with your audio file path

// Function to play harmoniser effect
~playHarmoniser = { |buffer, shift=1.5, mix=0.5|
    var synth = Synth(\harmoniser, [\bufnum, buffer, \shift, shift, \mix, mix]);
    buffer.duration;
};

// Function to play spectral splitting effect
~playSpectralSplit = { |buffer, splitFreq=1000, upperShift=1.5, mix=0.5|
    var synth = Synth(\spectralSplit, [\bufnum, buffer, \splitFreq, splitFreq, \upperShift, upperShift, \mix, mix]);
    buffer.duration;
};

~playSpectralSplit.(b, 1000, 1.5, 0.5).wait;

)

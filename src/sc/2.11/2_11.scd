(
// Define the SynthDef for spectral shifting
SynthDef(\spectralShift, {
    arg out = 0, bufnum, shiftAmount = 100;
    var in, chain, shiftedChain;

    // Input signal from buffer
    in = PlayBuf.ar(1, bufnum, loop: 1);

    // Perform FFT on the input signal
    chain = FFT(LocalBuf(2048), in);

    // Shift the frequency bins
    shiftedChain = PV_MagShift(chain, shiftAmount);

    // Perform IFFT to convert back to time domain
    Out.ar(out, IFFT(shiftedChain).dup);
}).add;

// Load the buffer and create the synth
b = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/src/sc/sounds/RickieLeeJones.wav");

// Play the buffer with spectral shifting
Synth(\spectralShift, [\bufnum, b.bufnum, \shiftAmount, -2.0]);
)

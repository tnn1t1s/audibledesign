// Load the buffer
(
~buf = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/sounds/wishart/3.1.wav"); // Make sure this file exists in your sounds folder
)




// Spectral stretching SynthDef
(
SynthDef(\spectralStretch, {
    arg out=0, bufnum=0, stretch=1, rate=1;
    var in, chain, stretched;
    
    in = PlayBuf.ar(1, bufnum, rate, loop: 0);
    chain = FFT(LocalBuf(2048), in);
    
    //stretched = PV_BinShift(chain, stretch , 0, 1);
	stretched = PV_BinShift(chain, 1 , stretch, 1);
    
    Out.ar(out, IFFT(stretched).dup);
}).add;
)



Synth(\spectralStretch, [\bufnum, ~buf, \stretch, 1.0])
Synth(\spectralStretch, [\bufnum, ~buf, \stretch, 1.2])
Synth(\spectralStretch, [\bufnum, ~buf, \stretch, 1.4])
Synth(\spectralStretch, [\bufnum, ~buf, \stretch, 1.6])
Synth(\spectralStretch, [\bufnum, ~buf, \stretch, 1.8])
Synth(\spectralStretch, [\bufnum, ~buf, \stretch, 1.2])



// Remember to free the buffer when done
 ~buf.free;

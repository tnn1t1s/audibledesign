// First, load a sound file
// Replace "/path/to/your/soundfile.wav" with the actual path to your audio file
~speechBuffer = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/sounds/RickieLeeJones.wav");

(
SynthDef(\enhancedVocoder, {
    arg carFreq=200, bufnum, rate=1, startPos=0, numBands=40, vowelEnh=1, dryMix=0.01, out=0;
    var carrier, modulator, signal, mixedSignal;
    var lowFreq = 100, highFreq = 5000;
    
    // Carrier signal (mixture of saw and pulse for richer harmonics)
    carrier = Mix([Saw.ar(carFreq), Pulse.ar(carFreq, 0.5)]) * 0.5;
	
    
    // Modulator signal (audio file)
    modulator = PlayBuf.ar(2, bufnum, rate, startPos: startPos, loop: 1);
    
    // Vocoding process
    signal = 20.collect({ |i|
        var freq = lowFreq * ((highFreq / lowFreq) ** (i / (numBands - 1)));
        var bandwidth = freq * 0.22; // Adjusted bandwidth
        var carrierBand = BPF.ar(carrier, freq, bandwidth / freq);
        var modulatorBand = BPF.ar(modulator, freq, bandwidth / freq);
        var envelope = Amplitude.ar(modulatorBand, 0.005, 0.1);
        
        // Vowel enhancement
        var vowelEnhanced = (modulatorBand * vowelEnh) + (envelope * (1 - vowelEnh));
        
        carrierBand * vowelEnhanced;
    }).sum;
    
    // Final processing
    //signal = Compander.ar(signal, signal, 0.5, 1, 0.5, 0.01, 0.1);
    
    // Mix vocoded signal with dry modulator
    mixedSignal = (signal * (1 - dryMix)) + (modulator * dryMix);
	mixedSignal = LeakDC.ar(mixedSignal); // Remove DC offset
    Out.ar(out, Pan2.ar(mixedSignal, 0, 0.5));
}).add;
)

// Play the enhanced vocoder with the loaded sound file
x = Synth(\enhancedVocoder, [\bufnum, ~speechBuffer, \carFreq, 200, \numBands, 40, \vowelEnh, 0.7]);

// Experiment with different settings
x.set(\carFreq, 440);   // Change carrier frequency
x.set(\rate, 1.0);      // Slightly speed up the playback of the audio file
x.set(\dryMix, 0.04);
x.set(\numBands, 60);   // Increase number of frequency bands
x.set(\vowelEnh, 1.0 );  // Adjust vowel enhancement

// Stop the synth
x.free;

// Remember to free the buffer when you're done
~speechBuffer.free;

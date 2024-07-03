/*
This piece exemplifies Xenakis' approach to organizing sound materials using statistical methods and mathematical models.
Key points about Pithoprakta and this example include:

Portamenti (pitch slides) are a central element of the composition.
These portamenti are organized in statistical fields governed by Poisson's formula, introducing an element of controlled randomness.
The piece includes "portamenti of portamenti" - complex pitch movements where the direction and rate of pitch change itself changes over time.
These pitch movements rise and fall without settling on any definite Hpitch (pitch in the traditional harmonic sense).
The result is a texture of multiple, interweaving glissandi that create a complex, evolving sound mass.
This approach demonstrates how pitched sounds can be manipulated in ways that go beyond traditional melodic or harmonic structures, focusing instead on the statistical properties of pitch movement.
The example illustrates how a seemingly unstable or indefinite pitch can emerge from many different pitches scattered over a narrow frequency band.

This example is significant as it showcases Xenakis' innovative approach to composition, blending mathematical concepts with musical elements to create unique sonic textures. It represents a departure from traditional pitch organizations, exploring the continuum of pitch space rather than discrete pitch classes.
*/

(
// Define a SynthDef for our glissandi
SynthDef(\glissando, {
    arg out=0, freq1=200, freq2=300, dur=1, pan=0, amp=0.1;
    var sig, env, freq;
    // Create a frequency envelope for the glissando
    freq = EnvGen.kr(Env([freq1, freq2], [dur], \lin));
    // Generate the signal
    sig = SinOsc.ar(freq) * amp;
    // Apply an amplitude envelope
    env = EnvGen.kr(Env.perc(0.01, dur), doneAction: 2);
    // Pan the signal
    sig = Pan2.ar(sig * env, pan);
    Out.ar(out, sig);
}).add;


// Function to generate random glissandi
~makeGlissandi = {
    arg num=10, minFreq=100, maxFreq=1000, minDur=0.5, maxDur=2;
    num.do {
        var freq1, freq2, dur, pan;
        // Generate random parameters for each glissando
        freq1 = exprand(minFreq, maxFreq);
        freq2 = exprand(minFreq, maxFreq);
        dur = rrand(minDur, maxDur);
        pan = rrand(-1.0, 1.0);
        
        // Schedule the glissando
        s.makeBundle(rrand(0, 5), {
            Synth(\glissando, [
                \freq1, freq1,
                \freq2, freq2,
                \dur, dur,
                \pan, pan,
                \amp, 0.05
            ]);
        });
    };
};

// Create multiple layers of glissandi
~makeGlissandi.(20, 100, 500, 0.5, 2);  // Lower frequency range
~makeGlissandi.(15, 300, 1000, 0.3, 1.5);  // Middle frequency range
~makeGlissandi.(10, 800, 2000, 0.2, 1);  // Higher frequency range

// Create "portamenti of portamenti"
10.do {
    var startFreq = exprand(100, 1000);
    var endFreq = exprand(100, 1000);
    var dur = rrand(3, 8);
    var numSegments = rrand(5, 10);
    
    // Create a series of connected glissandi
    numSegments.do { |i|
        var segmentDur = dur / numSegments;
        var freq1 = startFreq.linexp(0, numSegments, startFreq, endFreq, i);
        var freq2 = startFreq.linexp(0, numSegments, startFreq, endFreq, i+1);
        
        s.makeBundle(i * segmentDur, {
            Synth(\glissando, [
                \freq1, freq1,
                \freq2, freq2,
                \dur, segmentDur,
                \pan, rrand(-1.0, 1.0),
                \amp, 0.03
            ]);
        });
    };
};
)

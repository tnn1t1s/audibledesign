(
SynthDef(\spectralStretch, {
    var numPartials, fundamental, partials, stretchLFO, output;

    // Set constants
    numPartials = 10;
    fundamental = 100;  // 100 Hz

    // Create an LFO for the stretch amount
    // This will go from 1 (no stretch) to 2 (octave stretch) over 10 seconds
    stretchLFO = Line.kr(1, 2, 10);

    // Create the partials
    partials = numPartials.collect({ |i|
        var freq, amp;
        
        // Calculate frequency with increasing stretch
        freq = fundamental * (i + 1) * (1 + (i * (stretchLFO - 1) / numPartials));
        
        // Simple amplitude rolloff
        amp = 1 / (i + 1);

        SinOsc.ar(freq, 0, amp);
    });

    // Mix the partials
    output = Mix(partials);

    // Output
    Out.ar(0, output * 0.1 ! 2);
}).add;
)

// Play the spectral stretching sound
x = Synth(\spectralStretch);

// To stop the sound, evaluate this line:
x.free;

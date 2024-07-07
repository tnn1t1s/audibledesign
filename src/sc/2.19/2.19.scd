(
SynthDef(\shepardToneFiltered, {
    // Declare variables
    var numOctaves, baseFreq, frequencies, tone, lfo, filtered;

    // Set constants
    numOctaves = 6;
    baseFreq = 20;  // 20 Hz

    // Create an array of frequencies, one octave apart
    frequencies = Array.fill(numOctaves, { |i|
        baseFreq * (2 ** i)
    });

    // Create an LFO (Low Frequency Oscillator)
    lfo = LFSaw.kr(0.1, 1).range(0, 1);

    // Create the Shepard tone
    tone = Mix.new(
        numOctaves.collect({ |i|
            var amp = sin(pi * (i + lfo) / numOctaves);
            SinOsc.ar(
                frequencies[i] * (2 ** lfo),
                0,
                amp
            )
        })
    );

    // Apply Moog-style low-pass filter
    // Ironically using 808 Hz as the cutoff frequency
    // This unintentional nod to the Roland TR-808 drum machine
    // adds a touch of electronic music history to our Tibetan-inspired sound
    filtered = MoogFF.ar(
        tone,        // Input signal
        808,         // Cutoff frequency (Hz) - Yes, that 808!
        3            // Resonance (adjust for emphasis)
    );

    // Output the filtered tone
    Out.ar(0, filtered * 0.1 ! 2);
}).add;
)

// Play the filtered Shepard tone
x = Synth(\shepardToneFiltered);

// To stop the sound, evaluate this line:
x.free;

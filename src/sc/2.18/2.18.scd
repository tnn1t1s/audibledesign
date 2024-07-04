(
SynthDef(\shepardTone, {
    arg out=0, fundamental=16.35, numOctaves=10, rate=0.1, direction=1;
    var freqs, amps, phases, snd;

    // Generate frequencies for each octave
    freqs = Array.fill(numOctaves, { |i|
        fundamental * (2 ** i)
    });

    // LFO for frequency modulation
    freqs = freqs * LFSaw.kr(rate, 1, 0.5, 1.5);

    // Generate amplitudes for each octave
    amps = Array.fill(numOctaves, { |i|
        var normFreq = (fundamental * (2 ** i)) / 16000;
        (1 - cos(2pi * normFreq)) / 2
    });

    // Apply direction to amplitudes
    amps = if(direction > 0, amps, amps.reverse);

    // Generate phases
    phases = Array.fill(numOctaves, { Rand(0, 2pi) });

    // Generate the sound
    snd = SinOsc.ar(freqs, phases, amps).sum;

    Out.ar(out, snd ! 2);
}).add;

// Function to play Shepard Tone
~playShepardTone = { |fundamental=16.35, numOctaves=10, rate=0.1, direction=1|
    Synth(\shepardTone, [
        \fundamental, fundamental,
        \numOctaves, numOctaves,
        \rate, rate,
        \direction, direction
    ]);
};

// Play ascending Shepard Tone
~playShepardTone.(16.35, 10, 0.1, 1);

// Wait for 10 seconds
s.waitForBoot {
    10.wait;

    // Play descending Shepard Tone
    ~playShepardTone.(16.35, 10, 0.1, 0);
};
)

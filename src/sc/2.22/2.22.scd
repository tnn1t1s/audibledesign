(
SynthDef(\vowelFormants, {
    arg out=0, fundamental=200, formant=1, vowelIndex=0, amp=0.3;
    var source, filter, vowels, result;

    // Noise source for unvoiced sound
    source = PinkNoise.ar();

    // Vowel formants (approximate frequencies for [a, e, i, o, u])
    vowels = [
        [730, 1090, 2440],  // a
        [660, 1720, 2410],  // e
        [270, 2290, 3010],  // i
        [300, 870, 2240],   // o
        [300, 870, 2240]    // u (simplified, using 'o' formants)
    ];

    // Create formant filter
    filter = Select.ar(vowelIndex, vowels.collect { |freqs|
        freqs.collect { |freq|
            BPF.ar(source, freq * formant, 0.1)
        }.sum
    });

    result = filter * EnvGen.kr(Env.perc(0.01, 0.5), doneAction: 2);
    Out.ar(out, Pan2.ar(result * amp, 0));
}).add;

// Function to play a sequence of vowels
~playVowelSequence = { |formantShift=1|
    var vowels = [0, 1, 2, 0, 4];  // [a, e, i, a, u]
    Routine({
        vowels.do { |vowel, i|
            Synth(\vowelFormants, [
                \fundamental, 200,
                \formant, formantShift,
                \vowelIndex, vowel,
                \amp, 0.3
            ]);
            0.7.wait;
        };
    }).play;
};

// Play original vowel sequence
~playVowelSequence.value(1);

// Wait and then play formant-shifted sequence
s.waitForBoot {
    fork {
        4.wait;
        "Playing formant-shifted sequence".postln;
        ~playVowelSequence.value(4);  // Lowered formants for "lion-like" effect
    }
};
)

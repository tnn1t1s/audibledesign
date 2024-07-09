(
SynthDef(\noisyChord, {
    arg out=0, freqs=#[261.6, 329.6, 392.0, 523.2], startQ=50, endQ=200, dur=10, amp=0.5;
    var source, filters, env, qEnv;

    // White noise source
    source = WhiteNoise.ar();

    // Q envelope
    qEnv = EnvGen.kr(Env([startQ, endQ], [dur], \exp));

    // Filter bank
    filters = freqs.collect { |freq|
        BPF.ar(source, freq, 1/qEnv, 1/freqs.size)
    };

    // Sum all filters
    filters = Mix(filters);

    // Overall envelope
    env = EnvGen.kr(Env.linen(0.1, dur - 0.2, 0.1), doneAction: 2);

    Out.ar(out, Pan2.ar(filters * env * amp, 0));
}).add;

// Play the noisy chord with increasing Q
s.waitForBoot {
    Synth(\noisyChord, [
        \freqs, [261.6, 600, 1200, 1523.2],  // C4, E4, G4, C5
        \startQ, 20,
        \endQ, 100,
        \dur, 10,
        \amp, 0.5
    ]);

    "Playing noisy chord with increasing Q".postln;
};
)

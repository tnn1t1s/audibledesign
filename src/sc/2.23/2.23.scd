(
SynthDef(\jetNoise, {
    arg out=0, dur=10, amp=0.5;
    var source, filter, formantShift, env, result;

    // White noise source
    source = WhiteNoise.ar();

    // Envelope for formant shift: takeoff then landing
    env = Env(
        [1, 4, 1],  // Start low, peak in middle, end low
        [dur * 0.5, dur * 0.5],  // Equal time for takeoff and landing
        [2, -2]  // Curve shapes
    );

    // Formant shift envelope
    formantShift = EnvGen.kr(env);

    // Create formant filter (simulating jet engine resonances)
    filter = BPF.ar(source, [800, 2000, 3500] * formantShift, [0.1, 0.05, 0.05]).sum;

    // Amplitude envelope
    result = filter * EnvGen.kr(Env.linen(0.1, dur - 0.2, 0.1), doneAction: 2);

    Out.ar(out, Pan2.ar(result * amp, 0));
}).add;

// Play the combined takeoff and landing
Synth(\jetNoise, [\dur, 10, \amp, 0.5]);
)

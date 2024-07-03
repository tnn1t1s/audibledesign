

(
// Define a TB-303-like synth
// Define a TB-303-like synth with slide and accent
SynthDef(\tb303, {
    arg freq = 220, amp = 0.5, dur = 0.25, cutoff = 1000, res = 0.95, envMod = 0.5, accent = 0, slide = 0, legato = 0.9;
    var env = EnvGen.kr(Env.perc(0.01, dur * legato - 0.01), doneAction: 2);
    var glideFreq = Lag.kr(freq, slide * 1.0); // Glide effect
    var osc = Saw.ar(glideFreq) * env * (amp + accent * 0.5); // Use Saw for a sharper sound
    var filtEnv = EnvGen.kr(Env.perc(0.01, dur * 0.5), timeScale: dur) * envMod;
    var filt = RLPF.ar(osc, cutoff * (1 + filtEnv), res);
    Out.ar(0, filt ! 2);
}).add;


SynthDef(\tb3032, {
    arg freq = 440, amp = 0.5, dur = 0.25, cutoff = 1000, res = 0.95, envMod = 0.1, accent = 0, slide = 0.1, legato = 0.9;
    var env = EnvGen.kr(Env.perc(0.01, dur * legato - 0.01), doneAction: 2);
    var glideFreq = Lag.kr(freq, slide * 1.5); // Glide effect
    var osc = Saw.ar(glideFreq) * env * (amp + accent * 0.5); // Use Saw for a sharper sound
    var filtEnv = EnvGen.kr(Env.perc(0.01, dur * 0.5), timeScale: dur) * envMod;
    //var filt = RLPF.ar(osc, cutoff * (1 + filtEnv), res);
	var filt = MoogFF.ar(osc, cutoff * (1 + filtEnv), res);
    var accentEnv = EnvGen.kr(Env.perc(0.05, dur), timeScale: dur) * accent;
    Out.ar(0, (filt + accentEnv) ! 2);
}).add;



// Set the tempo
~clock = TempoClock.new(134/60);

// Notes pattern for "Higher State of Consciousness"
~pattern = [
    [43, 0.25, 1, 1, 0],  // g2 A S
    [55, 0.25, 1, 0, 0],   // g3 A
    [43, 0.25, 1, 1, 1],  // g2 A S
    [43, 0.25, 1, 1, 1],  // g2 A S
    [43, 0.25, 1, 0, 0],  // g2 A
    [43, 0.25, 1, 0, 0],  // g2 A
    [47, 0.25, 1, 1, 0],  // b2 A
    [43, 0.25, 1, 0, 0],  // g2 A
    [47, 0.25, 1, 1, 0],  // b2 A
    [43, 0.25, 1, 1, 1],  // g2 A S
    [43, 0.25, 1, 0, 0],  // g2 A
    [47, 0.25, 1, 1, 0],  // b2 A
    [43, 0.25, 1, 1, 1],  // g2 A S
    [43, 0.25, 1, 1, 1],  // g2 A S
    [43, 0.25, 1, 0, 0],  // g2 A
    [47, 0.25, 1, 1, 0]   // b2 A
];

// https://djjondent.blogspot.com/2020/04/famous-303-bassline-patterns-page-1.html

// Convert MIDI notes to frequencies and handle accents/slides
~convertToFreq = { |pattern|
    pattern.collect { |note|
        [note[0].midicps, note[1], note[2], note[3], note[4]]
    }
};

~patternFreq = ~convertToFreq.(~pattern);

// Play the pattern
Pbind(
    \instrument, \tb3032,
    \freq, Pseq(~patternFreq.collect({ |note| note[0] }), 4),
    \dur, Pseq(~patternFreq.collect({ |note| note[1] }), 4),
    \cutoff, 340,
    \res, 0.95,
    \envMod, 0.15,
    \accent, Pseq(~patternFreq.collect({ |note| note[2] }), 4),
    \slide, Pseq(~patternFreq.collect({ |note| note[3] }), 4),
    \legato, Pseq(~patternFreq.collect({ |note| if (note[3] == 1) { 1.4 } { 0.5 } }), 4)
).play(~clock);
)


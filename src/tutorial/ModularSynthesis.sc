# SuperCollider Tutorial: Audio Bus System for Modular Synthesis

In this tutorial, we'll explore SuperCollider's audio bus system by creating a virtual modular synthesizer. We'll implement common modules you'd find in a hardware modular synth, including oscillators, LFOs, an envelope generator, filters, VCA, and a mixer. This approach will demonstrate how to use audio and control buses to create complex sound structures.

## 1. Setting Up

First, let's start the SuperCollider server and create some buses:

```supercollider
s.boot;

// Create audio buses
~oscBus1 = Bus.audio(s, 1);
~oscBus2 = Bus.audio(s, 1);
~oscBus3 = Bus.audio(s, 1);
~filterBus = Bus.audio(s, 1);
~vcaBus = Bus.audio(s, 1);

// Create control buses for modulation
~lfo1Bus = Bus.control(s, 1);
~lfo2Bus = Bus.control(s, 1);
~envBus = Bus.control(s, 1);
```

## 2. Oscillators

Let's create three oscillators: square, sine, and saw.

```supercollider
(
SynthDef(\squareOsc, {
    arg freq=440, out=0;
    var sig = Pulse.ar(freq);
    Out.ar(out, sig);
}).add;

SynthDef(\sineOsc, {
    arg freq=440, out=0;
    var sig = SinOsc.ar(freq);
    Out.ar(out, sig);
}).add;

SynthDef(\sawOsc, {
    arg freq=440, out=0;
    var sig = Saw.ar(freq);
    Out.ar(out, sig);
}).add;
)

// Create oscillator instances
~osc1 = Synth(\squareOsc, [\out, ~oscBus1]);
~osc2 = Synth(\sineOsc, [\out, ~oscBus2]);
~osc3 = Synth(\sawOsc, [\out, ~oscBus3]);
```

## 3. LFOs

Now, let's create two LFOs: sine and square.

```supercollider
(
SynthDef(\sineLFO, {
    arg freq=1, out=0;
    var sig = SinOsc.kr(freq);
    Out.kr(out, sig);
}).add;

SynthDef(\squareLFO, {
    arg freq=1, out=0;
    var sig = LFPulse.kr(freq);
    Out.kr(out, sig);
}).add;
)

// Create LFO instances
~lfo1 = Synth(\sineLFO, [\out, ~lfo1Bus]);
~lfo2 = Synth(\squareLFO, [\out, ~lfo2Bus]);
```

## 4. Envelope Generator

Let's create a simple ADSR envelope generator.

```supercollider
(
SynthDef(\adsr, {
    arg gate=1, a=0.01, d=0.3, s=0.5, r=1, out=0;
    var env = EnvGen.kr(Env.adsr(a, d, s, r), gate, doneAction: 2);
    Out.kr(out, env);
}).add;
)

// Create envelope generator instance
~env = Synth(\adsr, [\out, ~envBus]);
```

## 5. Filter

Let's create a low-pass filter with a cutoff frequency modulation input.

```supercollider
(
SynthDef(\lpf, {
    arg in=0, out=0, cutoff=1000, res=0.5, modIn=0;
    var audio = In.ar(in);
    var mod = In.kr(modIn);
    var filteredAudio = RLPF.ar(audio, cutoff * mod.range(0.5, 2), res);
    Out.ar(out, filteredAudio);
}).add;
)

// Create filter instance
~filter = Synth(\lpf, [\in, ~oscBus1, \out, ~filterBus, \modIn, ~lfo1Bus]);
```

## 6. VCA

Now, let's create a Voltage Controlled Amplifier.

```supercollider
(
SynthDef(\vca, {
    arg in=0, out=0, modIn=0;
    var audio = In.ar(in);
    var mod = In.kr(modIn);
    Out.ar(out, audio * mod);
}).add;
)

// Create VCA instance
~vca = Synth(\vca, [\in, ~filterBus, \out, ~vcaBus, \modIn, ~envBus]);
```

## 7. Mixer

Finally, let's create a simple mixer to combine our signals.

```supercollider
(
SynthDef(\mixer, {
    arg in1=0, in2=0, in3=0, out=0;
    var sig1 = In.ar(in1);
    var sig2 = In.ar(in2);
    var sig3 = In.ar(in3);
    Out.ar(out, (sig1 + sig2 + sig3) * 0.33);
}).add;
)

// Create mixer instance
~mixer = Synth(\mixer, [\in1, ~vcaBus, \in2, ~oscBus2, \in3, ~oscBus3, \out, 0]);
```

## 8. Putting It All Together

Now that we have all our modules, let's connect them and make some sound!

```supercollider
(
// Set initial frequencies
~osc1.set(\freq, 440);
~osc2.set(\freq, 220);
~osc3.set(\freq, 330);

// Set LFO rates
~lfo1.set(\freq, 0.5);
~lfo2.set(\freq, 0.25);

// Set filter parameters
~filter.set(\cutoff, 2000, \res, 0.2);

// Trigger the envelope
~env.set(\gate, 1);

// Play the result
s.scope;
)

// Stop the sound
~env.set(\gate, 0);
```

## 9. Modulation Examples

Here are some examples of how to use modulation:

```supercollider
// Modulate oscillator 1 frequency with LFO 1
~osc1.map(\freq, ~lfo1Bus.asMap.linexp(-1, 1, 200, 800));

// Modulate filter cutoff with LFO 2
~filter.map(\cutoff, ~lfo2Bus.asMap.linexp(-1, 1, 500, 5000));

// Modulate oscillator 2 frequency with the envelope
~osc2.map(\freq, ~envBus.asMap.linexp(0, 1, 110, 440));
```

## Conclusion

This tutorial demonstrates how to use SuperCollider's audio and control buses to create a virtual modular synthesizer. By connecting different modules through buses, you can create complex sound structures similar to those possible with hardware modular synthesizers.

Remember to experiment with different connections and parameters. SuperCollider's flexibility allows for countless possibilities in sound design and synthesis.

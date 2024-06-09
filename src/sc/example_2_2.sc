/*
The example uses two oscillators, one for each stereo channel. Each oscillator has vibrato applied, but the right channel's vibrato frequency is offset by 19 Hz compared to the left. This setup allows listeners to hear how vibrato frequency can change the perceived pitch and demonstrates the auditory system's ability to distinguish pitches that are modulated differently and played simultaneously. The broad envelope settings with a quick attack and long release highlight the vibrato effect over an extended duration, making the difference in pitch perception more apparent.
*/

(
// Define the synth
SynthDef(\offsetVibratos, {
    |out = 0, freq = 440, vibratoFreq = 5, vibratoDepth = 10, gate = 1|
    var env, envGen;
	var sourceLeft, sourceRight;
	var offset = 19;
    
    // Create a vibrato as a sine wave for the left channel
    var vibratoLeft = SinOsc.kr(vibratoFreq, 0, vibratoDepth, freq); 
	// Create vibrato as a sine wave for the right channel
    var vibratoRight = SinOsc.kr(vibratoFreq + offset, 0, vibratoDepth, freq);
    
	// Define an amplitude envelope
    env = Env.perc(0.1, 20, 2, -4); // Quick attack, medium release, sustain level at 0.5
    envGen = EnvGen.kr(env, gate, doneAction: 2); // Envelope generator, frees itself after finishing
    
    // Apply vibrato to the main oscillator frequency
    sourceLeft = SinOsc.ar(vibratoLeft, 0, envGen * 0.5); // Audio-rate oscillator for sound production
    sourceRight = SinOsc.ar(vibratoRight, 0, envGen * 0.5); // Audio-rate oscillator for sound production
    
    // Output the sound - pan each source to opposite sides
    Out.ar(out, [Pan2.ar(sourceLeft, -1), Pan2.ar(sourceRight, 1)]); 
}).add;
)

// Play the synth
Synth(\offsetVibratos);


/*
Experiment 2.0 - explore spatial audio phenomena by stacking even harmonics on one side
and odd harmonics on the other side of the stereo field; this highlights how our auditory
system can integrate and perceive these separated sounds as a cohesive auditory experience.
*/

SynthDef(\harmonicVoice, {
        |out = 0, freq = 311.3, gate = 1|
        var sound, evenHarmonics, oddHarmonics, evenPan, oddPan;
	    var numHarmonics = 20; // Total number of harmonics
        var amp = 1.0; // Base amplitude for harmonics
        var env, envGen; // Envelope and envelope generator variables
        
        // Define the envelope
        env = Env.perc(1.0, 3.0, 10.0 -4); // Attack time, release time, sustain level, curve
        envGen = EnvGen.kr(env, gate, doneAction: 2); // doneAction: 2 will free the synth when done
        
        // Generate harmonics and pan based on being odd or even
	    oddHarmonics = Mix.fill(10, { |i| 
		    SinOsc.ar(freq * (2 * i + 1), 0, amp * (1 / (2 * i + 1))) });

        evenHarmonics = Mix.fill(10, { |i|
		    SinOsc.ar((freq) * (2 * (i + 1)), 0, amp * (1 / (2 * (i + 1))))
        });

        // Pan odd harmonics to left (-1) and even harmonics to right (1)
        oddPan = Pan2.ar(oddHarmonics, -1);
        evenPan = Pan2.ar(evenHarmonics, 1);
        
        // Combine the panned signals and apply the envelope
        sound = (oddPan + evenPan) * envGen;
        
        Out.ar(out, sound);
}).add;
)

Synth(\harmonicVoice);

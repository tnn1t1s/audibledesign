(
SynthDef(\shepardToneMoving, {
    // Declare variables
    var numOctaves, baseFreq, frequencies, tone, lfo;

    // Set constants
    numOctaves = 6;
    baseFreq = 20;  // 20 Hz

    // Create an array of frequencies, one octave apart
    frequencies = Array.fill(numOctaves, { |i|
        baseFreq * (2 ** i)
    });

    // Create an LFO (Low Frequency Oscillator)
    // LFSaw generates a sawtooth wave that moves from -1 to 1
    // We adjust it to move from 0 to 1 over about 10 seconds
    lfo = LFSaw.kr(0.1, 1).range(0, 1);

    // Create the Shepard tone
    tone = Mix.new(
        numOctaves.collect({ |i|
            // Calculate the amplitude for each oscillator
            // This creates a moving "bell curve" shape
            var amp = sin(pi * (i + lfo) / numOctaves);
            
            // Create the oscillator with moving frequency and amplitude
            SinOsc.ar(
                frequencies[i] * (2 ** lfo),  // Frequency rises with LFO
                0,  // Phase
                amp  // Amplitude
            )
        })
    );

    // Output the tone
    Out.ar(0, tone * 0.1 ! 2);  // Reduce volume and send to both channels
}).add;
)

// Play the moving Shepard tone
x = Synth(\shepardToneMoving);

// To stop the sound, evaluate this line:
x.free;

// To stop the sound, evaluate this line:
x.free;

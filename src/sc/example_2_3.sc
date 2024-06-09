/*
This example illustrates a complex sequence of pitches, demonstrating various conceptual aspects of pitch and harmony:
1. Truly Random Pitches: The sequence begins with a series of truly random pitches, showcasing a complete lack of harmonic structure.
2. Pitches from a Harmonic Field: Following the pause, pitches are randomly selected from the C minor scale, demonstrating a defined harmonic field.
4. Approximate Harmonic Field: The sequence continues with pitches that approximate a harmonic field, where each pitch is rounded to the nearest 10 Hz (e.g., 440 remains 440, 331 becomes 330), illustrating how small deviations can alter the perceived harmony.
5. Locked Harmonic Field: Finally, the sequence repeats with exact pitches from the C minor harmonic field, highlighting the perceptual difference when pitches are precisely aligned versus approximately aligned in a harmonic context.

This progression from randomness to precision exemplifies how humans perceive and interpret pitch in music, from unstructured sounds to highly structured harmonic arrangements.
*/

(
// Define C minor scale frequencies
var cMinorScale = [261.63, 293.66, 311.13, 349.23, 392.00, 415.30, 466.16];

// Sequence construction
var sequence = Pbind(
    \instrument, \simpleSine,
    \freq, Pseq([
        Pseq(Array.fill(10, {rrand(200, 600)}), 1),  // Truly random pitches
        Rest(2),  // Two-second pause
        Prand(~cMinorScale, 10),  // Random pitches from C minor scale
		Rest(2),  // Two-second pause
        Pseq(~cMinorScale.collect({ |freq| (freq/10).round(1) * 10 }), 1), 
		Rest(2),  // Two-second pause// Rounded pitches
        Pseq(~cMinorScale, 1)  // Exact C minor scale
    ], 1),
    \dur, 0.1
).play;
)

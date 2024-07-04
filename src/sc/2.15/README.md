# Example 2.15 - Dual Pitch Techniques

This SuperCollider code demonstrates two techniques for generating multiple pitches from a single sound source: the harmoniser approach and spectral splitting. These techniques are explored in the context of electronic music production and sound design.

## Requirements

- SuperCollider (https://supercollider.github.io/)
- An audio file (WAV format recommended)

## Setup

1. Ensure SuperCollider is installed on your system.
2. Replace the file path in the `Buffer.read` line with the path to your own audio file:

   ```supercollider
   b = Buffer.read(s, "/path/to/your/audio/file.wav");
   ```

## Code Overview

The code consists of several parts:

1. `SynthDef(\harmoniser, {...})`: Implements the harmoniser approach.
2. `SynthDef(\spectralSplit, {...})`: Implements the spectral splitting technique.
3. `Buffer.read(...)`: Loads the audio file into a buffer.
4. `~playHarmoniser` and `~playSpectralSplit` functions: Convenient ways to play each effect.
5. Demo code: Plays the original sound followed by both effects.

## Techniques Explained

### Harmoniser Approach (Sound example 2.14)

This technique shifts the pitch of the entire sound without altering its duration, then mixes it with the original. It preserves the microfluctuations of the original sound, creating an effect similar to double-stopping on a stringed instrument.

### Spectral Splitting (Sound example 2.15)

This approach splits the spectrum at a specified frequency and applies pitch shifting only to the upper part. This creates two sets of partials implying different fundamentals, resulting in a split pitch effect.

## Usage

To use this code:

1. Copy the entire code block into a SuperCollider document.
2. Select all the code and execute it (Ctrl+Enter or Cmd+Enter).
3. You will hear:
   - The original sound
   - The harmoniser effect
   - The spectral splitting effect

## Customization

You can customize each effect by modifying the parameters in the function calls:

```supercollider
~playHarmoniser.(b, shift, mix);
~playSpectralSplit.(b, splitFreq, upperShift, mix);
```

For the harmoniser:
- `shift`: Pitch shift factor (e.g., 1.5 for a perfect fifth up)
- `mix`: Mix between original and shifted sound (0-1)

For spectral splitting:
- `splitFreq`: Frequency at which to split the spectrum (in Hz)
- `upperShift`: Pitch shift factor for the upper part of the spectrum
- `mix`: Mix between original and processed sound (0-1)

## Further Exploration

Experiment with different audio sources and parameter values to explore the unique characteristics of each technique. Pay attention to how they maintain or alter the microfluctuations and timbral qualities of the original sound.

## Reference

This example is based on concepts discussed in Trevor Wishart's "Audible Design," updated to use samples from well-known electronic music tracks. The techniques demonstrated here are commonly used in electronic music production to create rich, layered sounds and interesting harmonic effects.

The sound usedd in this example is form Atmospheric Choral BRM 10 released by Bruton Music.


##

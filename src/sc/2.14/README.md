# Example 2.14 - Dual Pitch Effect

This SuperCollider code demonstrates a dual pitch effect, where the original sound is mixed with a pitch-shifted version of itself. This effect can create interesting harmonies and textures from a single audio source.

## Requirements

- SuperCollider (https://supercollider.github.io/)
- An audio file (MP3 format used in this example)

## Setup

1. Ensure SuperCollider is installed on your system.
2. Replace the file path in the `Buffer.read` line with the path to your own audio file:

   ```supercollider
   b = Buffer.read(s, "/path/to/your/audiofile.mp3");
   ```

## Code Overview

The code consists of several parts:

1. `SynthDef(\dualPitch, {...})`: Defines the synthesis process for the dual pitch effect.
2. `Buffer.read(...)`: Loads the audio file into a buffer.
3. `~playDualPitch` function: A convenient way to play the effect with customizable parameters.
4. Demo code: Plays the original sound followed by the dual pitch effect.

## Usage

To use this code:

1. Copy the entire code block into a SuperCollider document.
2. Select all the code and execute it (Ctrl+Enter or Cmd+Enter).
3. You should hear the original sound followed by the dual pitch effect.

## Customization

You can customize the effect by modifying the parameters in the `~playDualPitch` function call:

```supercollider
~playDualPitch.(b, pitchShift, mix);
```

- `pitchShift`: Controls the pitch shift amount. Values > 1 shift up, < 1 shift down. (e.g., 2 = one octave up)
- `mix`: Controls the balance between original and shifted sound. Range 0-1 (0 = only original, 1 = only shifted)

Examples:
- `~playDualPitch.(b, 2, 0.5)` - Mix original with version shifted up an octave
- `~playDualPitch.(b, 0.5, 0.7)` - Mix 30% original with 70% shifted down an octave

## Further Exploration

You can experiment with different `pitchShift` and `mix` values to create various effects. Try using extreme values or slowly varying these parameters over time for interesting results.

## Reference
The crowd sounds from Daft Punk's "Revolution 909" are sampled from the Hollywood Edge Sound Effects Library. The track uses various sound effects from this library, including the spoken word sample used in the song. This technique of integrating sound effects into their music adds a unique texture to Daft Punk's production style <<Wikpedia>>


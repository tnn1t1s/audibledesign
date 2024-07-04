# Example 2.16 - Dynamic Stereo Pitch Splitting

This SuperCollider code demonstrates a technique for dynamically splitting the pitch of a stereo sound. It builds upon the concepts introduced in Example 2.15, extending the idea of spectral splitting to create a gradual separation of partials across the stereo field.

## Concept

The effect starts with two stereo pairs containing the same set of partials and gradually separates them, creating a comb-like effect on each side of the stereo field. This dynamic application allows the pitch of a sound to gradually split in two, creating an interesting spatial and timbral transformation.

## Requirements

- SuperCollider (https://supercollider.github.io/)
- A stereo audio file (WAV format recommended)

## Setup

1. Ensure SuperCollider is installed on your system.
2. Replace the file path in the `Buffer.read` line with the path to your stereo audio file:

   ```supercollider
   b = Buffer.read(s, "/path/to/your/stereo/audio/file.wav");
   ```

## Code Overview

The code consists of several parts:

1. `SynthDef(\dynamicStereoPitchSplit, {...})`: Defines the synthesis process for the dynamic stereo pitch splitting effect.
2. `~playDynamicStereoPitchSplit` function: A convenient way to play the effect with customizable parameters.
3. Buffer loading code: Loads the stereo audio file.
4. Demo code: Plays the effect with default parameters.

## Usage

To use this code:

1. Copy the entire code block into a SuperCollider document.
2. Replace the audio file path with your own.
3. Select all the code and execute it (Ctrl+Enter or Cmd+Enter).
4. You should hear your audio file being processed with the dynamic stereo pitch splitting effect.

## Customization

You can customize the effect by modifying the parameters in the `~playDynamicStereoPitchSplit` function call:

```supercollider
~playDynamicStereoPitchSplit.(buffer, splitFreq, maxShift, splitDuration);
```

- `splitFreq`: The frequency at which the spectrum is split (default: 1000 Hz)
- `maxShift`: The maximum pitch shift factor (default: 1.5)
- `splitDuration`: The time over which the effect gradually applies (default: 5 seconds)

Example:
```supercollider
~playDynamicStereoPitchSplit.(b, 2000, 2, 10);
```
This would split the spectrum at 2000 Hz, shift by up to an octave, over a duration of 10 seconds.

## Further Exploration

Experiment with different audio sources and parameter values to explore the range of effects possible with this technique. Pay attention to how the stereo image and timbre evolve over time as the partials are separated.

Consider combining this effect with other spectral processing techniques or applying it selectively to different frequency ranges for more complex transformations.

## References

This example is based on concepts discussed in Trevor Wishart's "Audible Design," adapted to use samples from well-known electronic music tracks. The technique demonstrated here is commonly used in electronic music production to create evolving, spatial sound textures.

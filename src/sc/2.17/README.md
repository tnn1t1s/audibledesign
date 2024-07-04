# Example 2.17 - Basic Chorus Effect

This SuperCollider code demonstrates a basic chorus effect. The effect is achieved by playing the original audio track alongside a slightly pitch-shifted version, creating a richer, fuller sound.

## Concept

The chorus effect is created by mixing two versions of the same audio:
1. The original audio track
2. A copy of the track that is slightly pitch-shifted

This implementation uses a simple tape-style pitch shifting, which affects both the pitch and the speed of the shifted track. This approach is different from more complex formant-preserving pitch shifting techniques.

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

1. `SynthDef(\basicChorus, {...})`: Defines the synthesis process for the basic chorus effect.
2. `~playBasicChorus` function: A convenient way to play the effect with customizable parameters.
3. Buffer loading code: Loads the stereo audio file.
4. Demo code: Plays the effect with default parameters.

## Usage

To use this code:

1. Copy the entire code block into a SuperCollider document.
2. Replace the audio file path with your own.
3. Select all the code and execute it (Ctrl+Enter or Cmd+Enter).
4. You should hear your audio file being processed with the basic chorus effect.

## Customization

You can customize the effect by modifying the parameters in the `~playBasicChorus` function call:

```supercollider
~playBasicChorus.(buffer, pitchShift, mix);
```

- `pitchShift`: The pitch shift factor for the second track (default: 1.01, i.e., 1% higher)
- `mix`: The balance between the original and shifted tracks (default: 0.5, i.e., equal mix)

Example:
```supercollider
~playBasicChorus.(b, 1.03, 0.3);
```
This would use a 3% pitch shift and mix 30% of the shifted audio with 70% of the original.

## Further Exploration

- Experiment with different `pitchShift` values to find the sweet spot for your audio material.
- Try automating the `mix` parameter for a more dynamic effect.
- Consider adding a slight delay to the shifted track for a more pronounced chorus effect.
- Explore how different types of audio material (e.g., vocals, guitar, synthesizer) respond to this effect.

## Notes

This simple chorus effect doesn't include some features found in more advanced chorus implementations, such as modulation of the pitch shift or multiple shifted voices. However, it demonstrates the core concept of the chorus effect and can be a starting point for more complex implementations.

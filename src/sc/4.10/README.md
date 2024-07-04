# Exercise 4.10 - Onset Study: Audio Stitching and Envelope Application

This SuperCollider script demonstrates techniques for studying and manipulating sound onsets. It combines three distinct audio files, stitches them together at zero crossings, and applies amplitude and filter envelopes to the resulting audio.

## Concept

The exercise focuses on three main aspects:
1. Stitching audio files together at zero crossings to minimize discontinuities.
2. Applying an ADSR (Attack, Decay, Sustain, Release) envelope to shape the overall amplitude of the sound.
3. Using a filter envelope to dynamically control the spectral content of the sound.

These techniques are crucial in sound design and electronic music production for creating smooth transitions between sounds and shaping the character of a sound over time.

## Requirements

- SuperCollider (https://supercollider.github.io/)
- Three audio files: 
  - vocal_aah.wav
  - train_whistle.wav
  - car_crash.wav

## Setup

1. Ensure SuperCollider is installed on your system.
2. Replace the file paths in the script with the actual paths to your audio files:
   ```supercollider
   ~vocalBuf = Buffer.read(s, "/path/to/vocal_aah.wav");
   ~whistleBuf = Buffer.read(s, "/path/to/train_whistle.wav");
   ~crashBuf = Buffer.read(s, "/path/to/car_crash.wav");
   ```

## Code Overview

The script consists of several parts:

1. Loading audio files into buffers.
2. A function to find zero crossings in a buffer.
3. A function to stitch buffers together at zero crossings.
4. A SynthDef that plays the stitched buffer with ADSR and filter envelopes.
5. Code to play the stitched sound and schedule its release.

## Usage

To use this code:

1. Copy the entire code block into a SuperCollider document.
2. Replace the audio file paths with your own.
3. Select all the code and execute it (Ctrl+Enter or Cmd+Enter).
4. You should hear the stitched audio files played back with the ADSR and filter envelopes applied.

## Customization

You can customize the effect by modifying these parameters:

- ADSR envelope: Adjust the values in `Env.adsr(0.01, 0.3, 0.5, 2)` for both the amplitude and filter envelopes.
- Filter cutoff: Change the `cutoff` argument in the SynthDef (default is 20000 Hz).
- Buffer stitching: Modify the `~findZeroCrossing` function to change how zero crossings are detected.

## Further Exploration

- Experiment with different audio files and observe how their onsets affect the overall sound.
- Try different ADSR settings and observe how they shape the sound over time.
- Explore other filter types or multiple filters in series.
- Implement real-time control over the envelope parameters using MIDI or OSC.

## Notes

This script assumes mono audio files. If your files are stereo, you'll need to modify the `PlayBuf.ar` line in the SynthDef to use 2 channels instead of 1.

The zero-crossing detection method used here is simple and may not work perfectly for all audio files. More sophisticated methods could be implemented for better results.

Remember that the quality of the stitching and the overall effect will depend largely on the characteristics of the input audio files.

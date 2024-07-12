# SuperCollider Portamento Effect (Audible Design Example 2.27)

This SuperCollider script demonstrates the portamento effect described in Trevor Wishart's "Audible Design" (Example 2.27). It creates pitch glides by mixing a sound with a slightly time-stretched or time-shrunk copy of itself.

## Description

The script implements both downward and upward portamenti:
- Downward portamento: Created by start-synchronizing the original and stretched sounds.
- Upward portamento: Created by end-synchronizing the original and stretched sounds.

The effect is achieved by playing the original sound alongside a time-stretched version and controlling their mix.

## Features

- Load and play any mono audio file
- Interactive GUI controls for:
  - Triggering downward or upward portamento
  - Adjusting the stretch factor (controls the rate of pitch change)
  - Mixing between the original and stretched signals
- Real-time control over effect parameters

## Requirements

- SuperCollider (tested on version 3.11.2, but should work on most recent versions)
- A mono audio file for input

## Usage

1. Replace "your_audio_file.wav" in the script with the name of your audio file.
2. Run each block of code in the SuperCollider IDE in order.
3. Execute `~playPortamento.value` to open the control GUI.
4. Use the GUI to play and control the portamento effect:
   - "Play Downward" button: Triggers downward portamento
   - "Play Upward" button: Triggers upward portamento
   - "Stretch Factor" slider: Adjusts the rate of pitch change
   - "Mix" slider: Controls the balance between original and stretched signals

## Notes

- The effect is most noticeable with sounds that have stable pitch content.
- Experiment with different stretch factors to find the most pleasing portamento effect.
- Remember to free the buffer (`~buf.free`) when you're done to release memory.

## References

Wishart, T. (1994). Audible Design: A Plain and Easy Introduction to Practical Sound Composition. Orpheus the Pantomime.

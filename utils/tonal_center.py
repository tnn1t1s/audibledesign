import argparse
import librosa
import numpy as np

def estimate_tonal_center(audio_path):
    """Estimates the tonal center of an audio file using librosa."""
    y, sr = librosa.load(audio_path, sr=None)  # Load the file with its native sample rate
    y_harm, y_perc = librosa.effects.hpss(y)  # Harmonic-percussive source separation

    hop_length=1024

    if len(y_harm) < hop_length:
        hop_length = len(y_harm) // 2  # Adjust hop_length if it's too large for the audio

    chromagram = librosa.feature.chroma_cqt(y = y_harm, sr=sr, hop_length=hop_length)

    # Compute mean chromagram over time
    chroma_mean = np.mean(chromagram, axis=1)

    # Estimate the key using the mean chromagram
    key = np.argmax(chroma_mean)  # Find the key with the highest mean chroma value
    key_names = ['C', 'C#', 'D', 'D#', 'E', 'F', 'F#', 'G', 'G#', 'A', 'A#', 'B']
    estimated_key = key_names[key % 12]

    return estimated_key

def main():
    # Setup command-line argument parsing
    parser = argparse.ArgumentParser(description="Estimate the tonal center of an audio file.")
    parser.add_argument("input_file", type=str, help="Path to the audio file for analysis.")

    args = parser.parse_args()

    # Call the function to estimate the tonal center
    estimated_key = estimate_tonal_center(args.input_file)
    
    # Output the estimated tonal center
    print(f"Estimated Tonal Center: {estimated_key}")

if __name__ == "__main__":
    main()


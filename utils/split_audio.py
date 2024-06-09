import argparse
from pydub import AudioSegment
from pydub.silence import split_on_silence

def split_audio(file_path, output_dir, noise_floor=-40, time_ms=500):
    """Splits the audio file into chunks based on silence detection."""
    # Load the audio file
    audio = AudioSegment.from_file(file_path)

    # Split the audio where silence is longer than 'time_ms' and quieter than 'noise_floor'
    chunks = split_on_silence(
        audio,
        min_silence_len=time_ms,
        silence_thresh=noise_floor,
        keep_silence=100  # Optional: include some silence at the start and end of each chunk
    )

    # Save each chunk as a separate file
    for i, chunk in enumerate(chunks):
        chunk_file = f"{output_dir}/chunk_{i:04d}.wav"  # Formats the index as a zero-padded four-digit number
        chunk.export(chunk_file, format="wav")
        print(f"Exported '{chunk_file}'")

def main():
    # Set up command line argument parsing
    parser = argparse.ArgumentParser(description="Split an audio file into chunks based on silence.")
    parser.add_argument("input_file", type=str, help="Path to the input audio file")
    parser.add_argument("output_dir", type=str, help="Directory to save the output chunks")
    parser.add_argument("--noise_floor", type=int, default=-40, help="Silence threshold in dBFS (default: -40 dBFS)")
    parser.add_argument("--time_ms", type=int, default=500, help="Minimum length of silence in milliseconds (default: 500 ms)")

    # Parse arguments
    args = parser.parse_args()

    # Execute the audio splitting function
    split_audio(args.input_file, args.output_dir, args.noise_floor, args.time_ms)

if __name__ == "__main__":
    main()


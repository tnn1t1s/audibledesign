import openai
import os

def load_file(file_path):
    """Load the content of a file."""
    with open(file_path, 'r', encoding='utf-8') as file:
        return file.read()

def save_file(content, file_path):
    """Save content to a file."""
    with open(file_path, 'w', encoding='utf-8') as file:
        file.write(content)

def convert_to_markdown(text, api_key):
    """Use the OpenAI API to convert text to structured Markdown format."""
    openai.api_key = api_key

    response = openai.Completion.create(
        engine="text-davinci-002",  # You might want to check the latest available models
        prompt="Convert the following text to Markdown with section breaks identified by all capital letters:\n\n" + text,
        max_tokens=2048  # Adjust based on your expected output length
    )
    return response.choices[0].text.strip()

def process_file(file_path, api_key):
    """Process a single file to convert its content to Markdown and save it."""
    text = load_file(file_path)
    markdown_text = convert_to_markdown(text, api_key)
    save_file(markdown_text, file_path.replace('.md', '_converted.md'))

def main():
    api_key = os.getenv("OPENAI_API_KEY")  # Assuming the API key is set in environment variables
    directory = '/path/to/your/markdown/files'  # Set your directory path here

    # Process each Markdown file in the directory
    for filename in os.listdir(directory):
        if filename.endswith('.md'):
            file_path = os.path.join(directory, filename)
            process_file(file_path, api_key)
            print(f"Processed {filename}")

if __name__ == "__main__":
    main()


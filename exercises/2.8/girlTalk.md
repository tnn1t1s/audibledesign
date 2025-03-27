# GirlTalk

Exercise 2.8 in Audible Design is the first really challenging one. The original is example is here:
    - https://icrdistribution.bandcamp.com/track/chapter-28



https://github.com/user-attachments/assets/3d5c00bc-7216-4572-afb3-0f64e39b7855



For my interpretation, I will start with this audio:
     - https://www.youtube.com/watch?v=Dixj6B_eoog

Then split the audio using pyDub
python ./utils/split_audio.py ./sounds/girlTalk.mp3 ./sounds/girlTalkPieces --noise_floor -50 --time_ms 300

This works very well.

Then, use supercollider to overlay sequence of tones around the harmonic center of each phrase in girlTalk to take an excursion on the exercise 2.8 version. 

Reference:
obvisously play on the mashup king GirlTalk's opus.
https://www.youtube.com/watch?v=uWzkK7tUjaU



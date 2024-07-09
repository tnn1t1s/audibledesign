(
// SynthDef for an 808-style snare with built-in pitch shifting
SynthDef(\pitchedSnare808, {
    arg out=0, freq=180, sustain=0.1, amp=0.5, pitchShift=1;
    var snd, env, pitched;
    
    // Envelope
    env = EnvGen.ar(Env.perc(0.005, sustain, 0.3, -4), doneAction: 2);
    
    // Body
    snd = SinOsc.ar(freq) * env;
    
    // Noise component
    //snd = snd + (PinkNoise.ar(2) * Line.ar(1, 0, 0.2));
	snd = (PinkNoise.ar(2) * Line.ar(1, 0, 0.2));
    
    // Apply pitch shifting
	/*
	0.2: This is the window size in seconds. 
    pitchShift: This is the pitch shift ratio. A value of 1 means no shift, 2 means up an octave, 0.5 means down an octave. 
    0.01: This is the pitch dispersion. It adds a random deviation to the pitch shift, measured in semitones. 
    0.01: This is the time dispersion. It adds a random deviation to the onset times of grains, measured in seconds. 
*/
    pitched = PitchShift.ar(snd, 0.1, pitchShift*5, 0.01, 0.01);
	//pitched = snd;
    
    Out.ar(out, Pan2.ar(pitched * amp, 0));
}).add;

// Function to play our pitched snare
~playPitchedSnare = { |pitchShift=1|
    Synth(\pitchedSnare808, [\freq, 200, \sustain, 0.8, \amp, 0.5, \pitchShift, pitchShift]);
};

// Play original snare
~playPitchedSnare.value(1);

)

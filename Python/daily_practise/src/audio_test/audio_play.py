from matplotlib import pyplot as plt
import scipy.io.wavfile as wav
import numpy as np

SAMPLE_RATE = 44100
DURATION = 5


def sin_wave(hz):
    x = np.linspace(0, 0.01, 1000, endpoint=False)
    y = np.sin(x * hz * 2 * np.pi)
    plt.plot(x, y)
    plt.xlabel("Time")
    plt.axhline(y=0, color='k')
    plt.show()


def write_wav(hz):
    x = np.linspace(0, DURATION, SAMPLE_RATE * DURATION, endpoint=False)
    tone = np.sin(x * hz * 2 * np.pi)
    normalized_tone = np.int16((tone / tone.max()) * 32767)
    wav.write(f"{hz}hz.wav", SAMPLE_RATE, normalized_tone)


if __name__ == '__main__':
    # sin_wave(200)
    write_wav(200)

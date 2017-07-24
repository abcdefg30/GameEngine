package gameengine.components;

import org.lwjgl.openal.AL10;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.util.WaveData;

/**
 * @author Team
 *
 */
public class AudioComponent extends Component {
	/** Buffers hold sound data. */
	private int id;
	private int sourcebuffer;
	public String src;

	public AudioComponent(String src) {
		super(ComponentType.AUDIO);
		this.src = src;
	}

	public int loadSound() {
		int buffer = AL10.alGenBuffers();

		WaveData waveFile = WaveData.create(src);

		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);

		waveFile.dispose();

		return buffer;
	}

	public void Source(int buffer) {

		id = AL10.alGenSources();
		AL10.alSourcef(id, AL10.AL_GAIN, 1);
		AL10.alSourcef(id, AL10.AL_PITCH, 1);
		AL10.alSource3f(id, AL10.AL_POSITION, 0, 0, 0);
		this.sourcebuffer = buffer;
	}

	public void Source() {
		id = AL10.alGenSources();
		AL10.alSourcef(id, AL10.AL_GAIN, 1);
		AL10.alSourcef(id, AL10.AL_PITCH, 1);
		AL10.alSource3f(id, AL10.AL_POSITION, 0, 0, 0);
	}

	public void play(float gain) {
		AL10.alSourcef(id, AL10.AL_GAIN, gain);
		AL10.alSourcei(id, AL10.AL_BUFFER, sourcebuffer);
		AL10.alSourcePlay(id);
	}

	public void play(int buffer) {
		AL10.alSourcei(id, AL10.AL_BUFFER, buffer);
		AL10.alSourcePlay(id);
	}

	public void delete() {
		AL10.alDeleteSources(id);
	}

}

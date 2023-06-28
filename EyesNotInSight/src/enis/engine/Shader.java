package penis.engine;

import static org.lwjgl.opengl.GL31.*;

/**
 * Laedt einen Shader als Vertex- und Fragment-Shader und stellt diesen zur Verfügung für die Nutzung mit OpenGL.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.1
 * @date 02.06.2022
 *
 */
public class Shader {
	
	private int shaderProgram;
	
	/**
	 * Laedt den Shader und kompiliert ihn.
	 * Die Pfade zu den Shadern werden gemäß {@link GameState#loadText(String)} angegeben.
	 * @param state Der GameState, in dem der Shader genutzt wird.
	 * @param vertexFilename Dateipfad zur Datei mit dem Code des Vertex-Shaders
	 * @param fragmentFilename Dateipfad zur Datei mit dem Code des Fragment-Shaders
	 */
	public Shader(GameState state, String vertexFilename, String fragmentFilename) {
		String vertexText = state.loadText(vertexFilename);
		String fragmentText = state.loadText(fragmentFilename);
		
		int[] errorCode = new int[1];
		
		int vertShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertShader, vertexText);
		glCompileShader(vertShader);
		glGetShaderiv(vertShader, GL_COMPILE_STATUS, errorCode);
		if(errorCode[0] == GL_FALSE) {
			String infoLog = glGetShaderInfoLog(vertShader);
			glDeleteShader(vertShader);
			throw new RuntimeException("Failed vertex shader compilation:" + vertexFilename + "\n" + infoLog);
		}
		
		int fragShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragShader, fragmentText);
		glCompileShader(fragShader);
		glGetShaderiv(fragShader, GL_COMPILE_STATUS, errorCode);
		if(errorCode[0] == GL_FALSE) {
			String infoLog = glGetShaderInfoLog(fragShader);
			glDeleteShader(fragShader);
			throw new RuntimeException("Failed fragment shader compilation:" + fragmentFilename + "\n" + infoLog);
		}
		
		
		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertShader);
		glAttachShader(shaderProgram, fragShader);
		glLinkProgram(shaderProgram);
		glGetProgramiv(shaderProgram, GL_LINK_STATUS, errorCode);
		if(errorCode[0] == GL_FALSE) {
			String infoLog = glGetProgramInfoLog(shaderProgram);
			glDeleteProgram(shaderProgram);
			glDeleteShader(vertShader);
			glDeleteShader(fragShader);
			throw new RuntimeException("Failed shader linking:" + vertexFilename + ", " + fragmentFilename + "\n" + infoLog);
		}
		
		glDetachShader(shaderProgram, vertShader);
		glDetachShader(shaderProgram, fragShader);
		
		glDeleteShader(vertShader);
		glDeleteShader(fragShader);
	}
	
	/**
	 * Bindet den Shader in die OpenGL-Rendering-Pipeline ein.
	 */
	public void startShader() {
		glUseProgram(shaderProgram);
	}
	
	/**
	 * Entfernt den Shader aus der OpenGL-Rendering-Pipeline.
	 */
	public void stopShader() {
		glUseProgram(0);
	}

}

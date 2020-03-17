#version 330 core
layout (location = 0) in vec3 aPos;   // the position variable has attribute position 0
  
out vec3 ourColor; // output a color to the fragment shader
out vec4 pos;

void main()
{
    gl_Position = vec4(aPos, 1.0);
    pos = gl_Position;
    ourColor = vec3(1.0f, 0.0f, 0.3f); // set ourColor to the input color we got from the vertex data
}
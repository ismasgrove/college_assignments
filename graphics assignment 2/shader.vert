#version 330 core
layout (location = 0) in vec3 aPos;
  
out vec3 ourColor;

uniform mat4 ortho;

void main()
{
    gl_Position = ortho * vec4(aPos, 1.0f);
    ourColor = vec3(1.0f, 0.0f, 0.3f);
}
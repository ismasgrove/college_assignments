#include <iostream>
#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include "Shader.h"
#include "Shape.h"
#include "A2D.h"
#include "N3D.h"
#include "Graph.h"
#include "Chart.h"
#include <vector>

void framebuffer_size_callback(GLFWwindow* window, int width, int height);
void processInput(GLFWwindow* window);

size_t index=0;

auto main() -> int {
	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	//
	GLFWwindow* window = glfwCreateWindow(800, 600, "Assignment 1", NULL, NULL);
	if (window == NULL)
	{
		std::cout << "Failed to create GLFW window" << std::endl;
		glfwTerminate();
		return -1;
	}
	glfwMakeContextCurrent(window);
	//
	if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress)) {
		std::cout << "Failed to initialize GLAD" << std::endl;
		return -1;
	}
	//
	glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);
	//
	Shader shaderProgram("shader.vert", "shader.frag");
	//
	unsigned int VAO[4], VBO[4];
	glGenVertexArrays(4, VAO);
	glGenBuffers(4, VBO);
	std::vector<Shape*> shapes = { new A2D(VAO[0], VBO[0]), new N3D(VAO[1], VBO[1]),
		new Graph(VAO[2], VBO[2]), new Chart(VAO[3], VBO[3]) };
	//
	Shape* shapePointer = shapes.at(index);
	//
	glEnable(GL_LINE_WIDTH);
	glEnable(GL_PROGRAM_POINT_SIZE);
	//
	while (!glfwWindowShouldClose(window)) {
		processInput(window);
		//
		glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);
		shaderProgram.use();
		shapePointer = shapes.at(index);
		shapePointer->draw();
		//
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	glDeleteVertexArrays(4, VAO);
	glDeleteBuffers(4, VBO);

	glfwTerminate();
}

void framebuffer_size_callback(GLFWwindow* window, int width, int height)
{
	glViewport(0, 0, width, height);
}

void processInput(GLFWwindow* window)
{
	if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
		glfwSetWindowShouldClose(window, true);
	if (glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS)
		index = 0;
	if (glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS)
		index = 1;
	if (glfwGetKey(window, GLFW_KEY_3) == GLFW_PRESS)
		index = 2;
	if (glfwGetKey(window, GLFW_KEY_4) == GLFW_PRESS)
		index = 3;
}
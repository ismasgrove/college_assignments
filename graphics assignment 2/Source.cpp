#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>

#include <glad/glad.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include "Shader.h"

/*
	this code is written in OpenGL Core Profile using GLM and GLFW libraries and uses Vertex Buffer Objects for increased performance.
*/


/*
	I made this struct only so I could override '<' and use the STL sort() function
	so the points are sorted in counter clockwise order
	std::sort() only requires '<' to be implemented so there was no need for the rest
*/
struct Point {
	float _x, _y, _z=0;
	Point(float x, float y) {
		_x = x;
		_y = y;
	}
	void translate(float xc, float yc) {
		_x += xc;
		_y += yc;
	}
	//sorts counter-clockwise
	bool operator<(Point b) {
		constexpr float pi = 3.14159265358979323846;
		if (std::atan2(_x, _y) * 180 / pi < std::atan2(b._x, b._y) * 180 / pi)
			return true;
		return false;
	}
};

/*
	Circle struct implements the algorithm that utilizes eight way symmetry
*/
struct Circle {
private:
	float _xc, _yc, _r, _cosd, _sind, _xk, _yk;
	std::vector<float> _vertices;
	std::vector<Point> _points;
	void calculatePoints() {
		while (_xk >= _yk) {
			//x,y
			_points.push_back({ _xk, _yk });
			//y,x
			_points.push_back({ _yk, _xk });
			//y,-x
			_points.push_back({ _yk, -_xk });
			//-x,y
			_points.push_back({ -_xk, _yk });
			//These 'edge points' don't utilize eight way symmetry, so they're excluded
			if (_xk != _r) {
				//-x,-y
				_points.push_back({ -_xk, -_yk });
				//-y,-x
				_points.push_back({ -_yk, -_xk });
				//-y,x
				_points.push_back({ -_yk , _xk });
				//x,-y
				_points.push_back({ _xk , -_yk });
			}
			//
			_xk = (_xk * _cosd - _yk * _sind);
			_yk = (_yk * _cosd + _xk * _sind);
		}
		sort();
		for (auto& p : _points) {
			p.translate(_xc, _yc);
			_vertices.push_back(p._x);
			_vertices.push_back(p._y);
			_vertices.push_back(p._z);
		}
	}
	//what I waas talking about earlier
	void sort() {
		std::sort(_points.rbegin(), _points.rend());
	}
public: 
	Circle(float xc, float yc, float r, float dtheta) {
		_xc = xc;
		_yc = yc;
		_r = r;
		_cosd = std::cos(dtheta);
		_sind = std::sin(dtheta);
		_xk = r;
		_yk = 0;
		calculatePoints();
	}
	std::vector<float>& getVertices() {
		return _vertices;
	}
	int getSize() {
		return _vertices.size();
	}
};

void framebuffer_size_callback(GLFWwindow* window, int width, int height);
void processInput(GLFWwindow* window);

int main() {
	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	//
	GLFWwindow* window = glfwCreateWindow(800, 800, "Assignment 2", NULL, NULL);
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
	
	/*
		this defines a circle with center (0,0) and radius 50
		dtheta is 0.2 for more details. after changing these parameters
		you should adjust the orthographic matrix accordingly

	*/
	Circle circle{ 0, 0, 50, 0.2 };
	std::vector<float> vertices = circle.getVertices();
	int vertices_count = circle.getSize() / 3;
	unsigned int VBO, VAO;
	glGenBuffers(1, &VBO);
	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO);
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(float), &vertices[0], GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);
	glEnable(GL_POINT_SIZE);
	//
	while (!glfwWindowShouldClose(window)) {
		processInput(window);
		glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);
		/*
			I'm projecting it in a way that just surrounds the circle defined above
		*/
		glm::mat4 ortho_mat = glm::ortho(-55.0f, 55.0f, -55.0f, 55.0f, -0.1f, 0.1f);
		int ortho = glGetUniformLocation(shaderProgram.ID, "ortho");
		glUniformMatrix4fv(ortho, 1, GL_FALSE, glm::value_ptr(ortho_mat));
		shaderProgram.use();
		glPointSize(5);
		glBindVertexArray(VAO);
		glDrawArrays(GL_POINTS, 0, vertices_count);
		glDrawArrays(GL_LINE_LOOP, 0, vertices_count);
		glfwSwapBuffers(window);
		glfwPollEvents();
	}


	glDeleteVertexArrays(1, &VAO);
	glDeleteBuffers(1, &VBO);

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
}
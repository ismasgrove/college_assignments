#pragma once
#include "Shape.h"
#include "Shader.h"
class Graph: public Shape
{
private:
	static const float vertices[];
	unsigned int& VAO, & VBO;
public:
	Graph(unsigned int& VAO, unsigned int& VBO);
	virtual void draw() override;
};


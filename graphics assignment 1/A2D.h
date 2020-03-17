#pragma once
#include "Shape.h"
class A2D: public Shape
{
private: 
	static const float vertices[];
	unsigned int& VAO, & VBO;
public:
	A2D(unsigned int& VAO, unsigned int& VBO);
	virtual void draw() override;
};


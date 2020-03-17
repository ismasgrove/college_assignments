#pragma once
#include "Shape.h"
class N3D: public Shape
{
private:
	static const float vertices[];
	unsigned int& VAO, & VBO;
public:
	N3D(unsigned int& VAO, unsigned int& VBO);
	virtual void draw() override;
};


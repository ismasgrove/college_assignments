#pragma once
#include "Shape.h"

class Chart : public Shape
{
private:
	static const float vertices[];
	unsigned int& VAO, & VBO;
public:
	Chart(unsigned int& VAO, unsigned int& VBO);
	virtual void draw() override;
};


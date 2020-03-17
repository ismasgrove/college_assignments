#pragma once
#ifndef SHAPE_H
#define SHAPE_H

#include <glad/glad.h>

class Shape
{
private:
	static const float vertices[];
public:
	virtual void draw()=0;
};

#endif
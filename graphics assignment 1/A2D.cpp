#include "A2D.h"

const float A2D::vertices[] = {
     0.5f, -0.5f, 0.0f,
     0.0f, 0.5f, 0.0f,
     -0.5f, -0.5f, 0.0f,
     0.0f, 0.5f, 0.0f,
     0.15f, 0.175f, 0.0f,
    -0.15f, 0.175f, 0.0f
};

A2D::A2D(unsigned int &VAO, unsigned int &VBO) : VAO(VAO), VBO(VBO) {
    glBindVertexArray(VAO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);
}

void A2D::draw()  {
    glBindVertexArray(VAO);
    glLineWidth(4);
    glDrawArrays(GL_LINES, 0, 6);
}
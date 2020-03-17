#include "N3D.h"

const float N3D::vertices[] = {
     -0.5f, -0.5f, 0.0f,
     -0.5f, 0.5f, 0.5f,
     0.5f, -0.5f, 0.0f,
     0.5f, 0.5f, 1.0f
};

N3D::N3D(unsigned int& VAO, unsigned int& VBO) : VAO(VAO), VBO(VBO) {
    glBindVertexArray(VAO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);
}

void N3D::draw() {
    glBindVertexArray(VAO);
    glLineWidth(4);
    glDrawArrays(GL_LINE_STRIP, 0, 4);
}
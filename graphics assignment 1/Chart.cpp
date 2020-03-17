#include "Chart.h"

const float Chart::vertices[] = {
    -1.0f, -1.0f, 0.0f,
    -0.2f, -0.2f, 0.0f,
    0.4f, -0.2f, 0.0f,
    0.9f, 0.7f, 0.0f
};

Chart::Chart(unsigned int& VAO, unsigned int& VBO) : VAO(VAO), VBO(VBO) {
    glBindVertexArray(VAO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);
}

void Chart::draw() {
    glBindVertexArray(VAO);
    glLineWidth(2);
    glDrawArrays(GL_LINE_STRIP, 0, 4);
}
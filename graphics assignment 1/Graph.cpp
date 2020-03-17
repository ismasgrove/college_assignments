#include "Graph.h"

const float Graph::vertices[] = {
    -0.6f, -0.5f, 0.0f,
    -0.2f, -0.3f, 0.0f,
    -0.2f, -0.5f, 0.0f,
    0.2f, -0.2f, 0.0f,
    -0.4f, 0.6f, 0.0f,
    0.3f, 0.6f, 0.0f
};

Graph::Graph(unsigned int& VAO, unsigned int& VBO) : VAO(VAO), VBO(VBO) {
    glBindVertexArray(VAO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);
}

void Graph::draw() {
    glBindVertexArray(VAO);
    glLineWidth(4);
    glPointSize(30);
    glDrawArrays(GL_LINE_STRIP, 0, 6);
    glDrawArrays(GL_POINTS, 0, 6);
}
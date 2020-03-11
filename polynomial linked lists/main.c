#include <stdio.h>
#include <stdlib.h>
#include <math.h>

typedef struct polyNode {
    int exponent, coefficient;
    struct polyNode *next;
} polyNode;

typedef struct polyLinkedList {
    polyNode *head, *tail;
} polyLinkedList;

polyNode* newPolyNode(int c, int e){
    polyNode *p = malloc(sizeof(polyNode));
    p->coefficient=c;
    p->exponent=e;
    p->next=NULL;
    return p;
}
void initialize(polyLinkedList *x){
   x->head=NULL;
   x->tail=NULL;
}
void polyDisplay(polyLinkedList *x){
    polyLinkedList *temp = x;
    while (temp->head->next){
        printf("%d^%d", x->head->coefficient, x->head->exponent);
        temp->head=temp->head->next;
    }
}

float getValue(polyLinkedList *x, float y){
    float answer=0;
    while (x->head->next){
        answer += x->head->coefficient*pow(y, x->head->exponent);
        x->head=x->head->next;
    }

    return answer;
}

polyLinkedList* polyAdd(polyLinkedList *x, polyLinkedList *y){
    polyLinkedList* answer;
    initialize(&answer);

    while (x->head->next){
        if (x->head->exponent==y->head->exponent){
            answer->head=newPolyNode(x->head->coefficient+y->head->coefficient, x->head->exponent);
        }
        answer->head=answer->head->next;

    }

    return answer;
}

polyLinkedList* polyMul(polyLinkedList *x, polyLinkedList *y){
    polyLinkedList* answer;
    initialize(&answer);

    while (x->head->next){
        if (x->head->exponent==y->head->exponent){
            answer->head=newPolyNode(x->head->coefficient*y->head->coefficient, x->head->exponent);
        }
        answer->head=answer->head->next;
    }
    return 0;
}

int main()
{
    int decision;
    int i, ph1, ph2;
    float ph3;
    printf("Choose an operation:\n1-Add\n2-Multiply\n3-getValue\n0-Terminate");
    scanf("%d", &decision);
    polyLinkedList *x, *y;
    initialize(&x);
    initialize(&y);
    while (decision!=0) {
        if (decision==1){
            for (i=0; i<3; i++){
            printf("Enter Coefficient:");
            scanf("%d", &ph1);
            printf("Enter Exponent:");
            scanf("%d", &ph2);
            x->head=newPolyNode(ph1, ph2);
            if (i!=20){
                x->head=x->head->next;
                }
            }
            for (i=0; i<3; i++){
            printf("Enter Coefficient:\t");
            scanf("%d", &ph1);
            printf("Enter Exponent:\t");
            scanf("%d", &ph2);
            y->head=newPolyNode(ph1, ph2);
            if (i!=20){
                y->head=y->head->next;
                }
            }
            polyLinkedList *answer = polyAdd(&x, &y);
            polyDisplay(answer);
        }
        if (decision==2){
            for (i=0; i<3; i++){
            printf("Enter Coefficient:\t");
            scanf("%d", &ph1);
            printf("Enter Exponent:\t");
            scanf("%d", &ph2);
            x->head=newPolyNode(ph1, ph2);
            if (i!=20){
                x->head=x->head->next;
                }
            }
            printf("For the second polynomial:\n");
            for (i=0; i<3; i++){
            printf("Enter Coefficient:");
            scanf("%d", &ph1);
            printf("Enter Exponent:");
            scanf("%d", &ph2);
            y->head=newPolyNode(ph1, ph2);
            if (i!=20){
                y->head=y->head->next;
                }
            }
            polyLinkedList *answer = polyMul(&x, &y);
            polyDisplay(answer);
        }
        if (decision==3){
            printf("Enter value for X:");
            scanf("&d", &ph3);
            for (i=0; i<3; i++){
            printf("Enter Coefficient:\t");
            scanf("%d", &ph1);
            printf("Enter Exponent:\t");
            scanf("%d", &ph2);
            x->head=newPolyNode(ph1, ph2);
            if (i!=20){
                x->head=x->head->next;
                }
            }
            float answer = getValue(x, ph3);
            printf("The result is: %f", answer);
        }
    }
}

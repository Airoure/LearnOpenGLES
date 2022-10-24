precision mediump float;

uniform vec4 u_Color;

void main() {
    float r = 0.0 , delta = 0.0 , alpha = 1.0;
    vec2 cxy = 2.0 * gl_PointCoord - 1.0;
    r = dot(cxy,cxy);

    alpha = 1.0 - smoothstep(0.85, 1.0, r);
    gl_FragColor = u_Color * alpha;
}


// See LICENSE for license details.

package example.test

import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}
import example.FullAdder

class FullAdderUnitTester(c: FullAdder) extends PeekPokeTester(c) {

  private val fa = c

  printf("c a b   o s\n");
  for(cin <- 0 to 1 by 1) {
    for (in1 <- 0 to 1 by 1) {
      for (in0 <- 0 to 1 by 1) {
        val res = cin + in1 + in0
        val sum = res & 1
        val cout= (res>>1) & 1
        poke(fa.io.cin, cin)
        poke(fa.io.in1, in1)
        poke(fa.io.in0, in0)
        step(1)
        expect(fa.io.cout, cout)
        expect(fa.io.sout, sum)
        val sout = peek(fa.io.sout)
        val cout_print = peek(fa.io.cout)
        printf("%d %d %d   %d %d \n", cin, in1, in0, cout_print, sout);
      }
    }
  }
}

class FullAdderTester extends ChiselFlatSpec {
  private val backendNames = Array[String]("firrtl"/*, "verilator"*/)
  for ( backendName <- backendNames ) {
    "FullAdder" should s"Implement simple 1 bit fulladder (with $backendName)" in {
      Driver(() => new FullAdder, backendName) {
        c => new FullAdderUnitTester(c)
      } should be (true)
    }
  }
}

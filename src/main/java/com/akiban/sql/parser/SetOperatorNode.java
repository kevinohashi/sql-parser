/* Copyright (C) 2011 Akiban Technologies Inc.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

/* The original from which this derives bore the following: */

/*

   Derby - Class org.apache.derby.impl.sql.compile.SetOperatorNode

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to you under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.akiban.sql.parser;

import com.akiban.sql.StandardException;

/**
 * A SetOperatorNode represents a UNION, INTERSECT, or EXCEPT in a DML statement. Binding and optimization
 * preprocessing is the same for all of these operations, so they share bind methods in this abstract class.
 *
 * The class contains a boolean telling whether the operation should eliminate
 * duplicate rows.
 *
 */

abstract class SetOperatorNode extends TableOperatorNode
{
  /**
   ** Tells whether to eliminate duplicate rows.  all == TRUE means do
   ** not eliminate duplicates, all == FALSE means eliminate duplicates.
   */
  boolean all;

  OrderByList orderByList;
  ValueNode offset; // OFFSET n ROWS
  ValueNode fetchFirst; // FETCH FIRST n ROWS ONLY

  /**
   * Initializer for a SetOperatorNode.
   *
   * @param leftResult The ResultSetNode on the left side of this union
   * @param rightResult The ResultSetNode on the right side of this union
   * @param all Whether or not this is an ALL.
   * @param tableProperties Properties list associated with the table
   *
   * @exception StandardException Thrown on error
   */

  public void init(Object leftResult,
                   Object rightResult,
                   Object all,
                   Object tableProperties)
      throws StandardException {
    super.init(leftResult, rightResult, tableProperties);
    this.all = ((Boolean)all).booleanValue();
  }

  /**
   * Convert this object to a String.  See comments in QueryTreeNode.java
   * for how this should be done for tree printing.
   *
   * @return This object as a String
   */

  public String toString() {
    return "all: " + all + "\n" +
      super.toString();
  }

  /**
   * Prints the sub-nodes of this object.  See QueryTreeNode.java for
   * how tree printing is supposed to work.
   *
   * @param depth The depth of this node in the tree
   */

  public void printSubNodes(int depth) {
    super.printSubNodes(depth);

    if (orderByList != null) {
      printLabel(depth, "orderByList:");
      orderByList.treePrint(depth + 1);
    }
  }

  /**
   * @return the operator name: "UNION", "INTERSECT", or "EXCEPT"
   */
  abstract String getOperatorName();

}

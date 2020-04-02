Feature: Loans

Scenario:  Get the number of loans made in Paris
    Given this table which contains information about clients
    | clientId: Integer | loan: Boolean | agencyId: Integer   |
    |  01               | true          | 01                  |
    |  02               | false         | 01                  |
    |  03               | true          | 02                  |
    |  04               | false         | 02                  |
    |  05               | true          | 01                  |

    And agencies table
    | agencyId: Integer | agencyLocation: String           |
    | 01                | Paris                            |
    | 02                | Lyon                             |

    When I join the two tables and compute the number of clients whose made a loan in Paris

    Then the result
    |2|





